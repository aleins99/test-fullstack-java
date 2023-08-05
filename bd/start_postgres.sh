#!/usr/bin/env bash

set -euo pipefail
which psql > /dev/null || (echoerr "Please ensure that postgres client is in your PATH" && exit 1)

mkdir -p $HOME/docker/volumes/postgres
rm -rf $HOME/docker/volumes/postgres/data

docker run --rm --name pg-docker -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=dev -d -p 5433:5432 -v $HOME/docker/volumes/postgres:/var/lib/postgresql postgres
sleep 3
export PGPASSWORD=postgres
cat 01-esquemas.sql | docker exec -i pg-docker psql -U postgres -d dev
cat 02-datos.sql | docker exec -i pg-docker psql -U postgres -d dev