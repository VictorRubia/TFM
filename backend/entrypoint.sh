#!/bin/bash
set -e

echo "Argumentos pasados al script: $@"

# Crear el directorio tmp/pids si no existe
mkdir -p /tfm/tmp/pids

# Remove a potentially pre-existing server.pid for Rails.
rm -f /tfm/tmp/pids/server.pid

# Esperar a que la base de datos esté lista
until nc -z $PROD_DATABASE_URL $PROD_DATABASE_PORT; do
  echo "Esperando a que la base de datos esté disponible..."
  sleep 1
done

# Ejecutar migraciones y tareas de inicialización
echo "Ejecutando migraciones..."
bundle exec rails db:create RAILS_ENV=development || true
bundle exec rails db:migrate RAILS_ENV=development

# Then exec the container's main process (what's set as CMD in the Dockerfile).
exec "$@"