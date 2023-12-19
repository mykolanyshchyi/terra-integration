#!/bin/bash

set -o errexit
set -o nounset
set -o pipefail

PROJECT_VERSION="$1"
echo "Release version: ${PROJECT_VERSION}"

GIT_USER="$2"
echo "Git user: ${GIT_USER}"

echo "Starting release process"
mvn release:prepare --batch-mode -Dusername=${GIT_USER} release:perform
echo "release process complete"

git checkout tags/v${PROJECT_VERSION} -b v${PROJECT_VERSION}

mvn -DskipTests clean package

echo "Building docker image for fitness-health-app"
docker build --build-arg app_jar_src=fitness-health-app/target/fitness-health-app-${PROJECT_VERSION}.jar --tag docker-hub.sundev.team/fitness-health-app:${PROJECT_VERSION} -f fitness-health-app/Dockerfile .
echo "Pushing docker image for fitness-health-app"
docker push docker-hub.sundev.team/fitness-health-app:${PROJECT_VERSION}

