#!sh

a=$2
for i in images/*.png; do
  new=$(printf "images/$1_2%03d.png" "$a") 
  mv -- "$i" "$new"
  let a=a+1
done
