package de.baleipzig.iris.logic.worker;

import de.baleipzig.iris.ImageType;
import de.baleipzig.iris.logic.converter.ImageConverter;
import de.baleipzig.iris.model.image.IImage;
import de.baleipzig.iris.persistence.entity.image.ImageEntity;
import de.baleipzig.iris.persistence.repository.IImageEntityRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageWorker implements IImageWorker {

    private final Map<ImageType, List<IImage>> allImagesAsMap = new HashMap<>();

    private final IImageEntityRepository repository;

    @Override
    public long countImagesByType(ImageType imageType) {
        return repository.countByImageType(imageType.toString());
    }

    @Override
    public List<IImage> loadRandomImagesByType(int imageCount, ImageType imageType) {

        List<IImage> allImagesByType = loadAllImagesByType(imageType);
        int imageEntityCount = allImagesByType.size();
        imageCount = Math.min(imageCount, imageEntityCount);

        Collections.shuffle(allImagesByType);

        return allImagesByType.subList(0, imageCount);
    }

    @Override
    public List<IImage> loadAllImagesByType(ImageType imageType) {
        synchronized (allImagesAsMap) {
            if (allImagesAsMap.get(imageType) == null) {

                List<ImageEntity> allImageEntitiesByType = repository.findAllByImageType(imageType.toString());
                List<IImage> allImagesByType = new ArrayList<>(allImageEntitiesByType.size());

                allImageEntitiesByType.forEach(imageEntityByType -> {
                    IImage imageByType = ImageConverter.fromEntity(imageEntityByType);
                    allImagesByType.add(imageByType);
                });

                allImagesAsMap.put(imageType, allImagesByType);
            }

            return allImagesAsMap.get(imageType);
        }
    }

    @Override
    public void exportImageToDb() {
        repository.deleteAll();

        processImageFolder("C:\\mnist\\mnist-test", ImageType.TEST);
        processImageFolder("C:\\mnist\\mnist-training", ImageType.TRAIN);
    }


    private void processImageFolder(String pathToFolder, ImageType imageType) {
        final List<ImageEntity> imageEntities = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(pathToFolder))) {
            paths.forEach(filePath -> {
                try {
                    processFile(filePath, imageType, imageEntities);
                } catch (Exception e) {
                    System.out.println(e);
                }

            });
        } catch (IOException e) {
            System.out.println(e);
        }
        repository.save(imageEntities);
    }

    private void processFile(Path path, ImageType imageType, List<ImageEntity> imageEntities) throws Exception {
        if (Files.isRegularFile(path)) {
            byte[] data = Files.readAllBytes(path);

            String imageAsBase64 = Base64.getEncoder().encodeToString(data);
            ImageEntity imageEntity = new ImageEntity();
            String fileName = FilenameUtils.getBaseName(path.getFileName().toString());

            String[] fileNameSplitted = fileName.split("_");
            imageEntity.setName(fileName);
            imageEntity.setImageType(imageType.toString());
            imageEntity.setDigit(Integer.valueOf(fileNameSplitted[0]));
            imageEntity.setImageNumber(Integer.valueOf(fileNameSplitted[1]));
            imageEntity.setImageAsBase64(imageAsBase64);
            imageEntities.add(imageEntity);
        }
    }
}
