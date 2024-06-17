#!/bin/bash

# Read current version from a file (e.g., VERSION)
version=$(cat VERSION)

# Split version into components
IFS='.' read -r -a version_parts <<< "$version"

# Increment the PATCH version
version_parts[2]=$((version_parts[2] + 1))

# Join the version parts back together
new_version="${version_parts[0]}.${version_parts[1]}.${version_parts[2]}"

# Update the VERSION file
echo "$new_version" > VERSION

# Build Docker images with the new version tag
docker build -t data-producer:$new_version ./data-producer
docker build -t analytics:$new_version ./analytics
docker build -t playground:$new_version ./playground

# Optionally push to Docker registry
# docker push data-producer:$new_version
# docker push analytics:$new_version
# docker push playground:$new_version

echo "Built and tagged images with version $new_version"

# Set the version environment variable for Docker Compose
export VERSION=$new_version

# Append the new version to the .env file without overwriting existing content
if ! grep -q "VERSION=" .env; then
  echo "VERSION=$new_version" >> .env
else
  sed -i "s/VERSION=.*/VERSION=$new_version/" .env
fi
