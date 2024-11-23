#!/bin/bash
docker-compose run --no-deps web rails new . --force --css=bootstrap --database=mysql
docker-compose build

echo "default: &default
  adapter: mysql2
  encoding: utf8mb4
  pool: <%= ENV.fetch("RAILS_MAX_THREADS") { 5 } %>
  username: root
  password: admin
  host: db

development:
  <<: *default
  database: tfg_development

test:
  <<: *default
  database: tfg_test" > config/database.yml

docker-compose up
docker-compose run web rake db:create