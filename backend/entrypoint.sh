#!/bin/bash
set -e

# Crear el directorio tmp/pids si no existe
mkdir -p /tfm/tmp/pids

# Remove a potentially pre-existing server.pid for Rails.
rm -f /tfm/tmp/pids/server.pid

# Then exec the container's main process (what's set as CMD in the Dockerfile).
exec "$@"
