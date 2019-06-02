#!/bin/bash

for filename in ../bpp_instances/*.bpp; do
	echo "$filename"
	./bpp "$filename" >> output.txt
done