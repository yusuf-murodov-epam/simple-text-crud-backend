# Simple text crud application
Save magic text into database.

## Build image:
Production - `$docker build -t simple-text-crud:1.1 --target=production --rm .`

Development - `$docker build -t simple-text-crud:1.0 --target=development --rm .`

## Start service:
Run with default environment: 

`$docker-compose -f docker-compose.yaml --env-file .config/.env up`

Run with production environment: 

`$docker-compose -f docker-compose.yaml --env-file .config/.env.prod up`

Run with development environment: 

`$docker-compose -f docker-compose.yaml --env-file .config/.env.dev up`
