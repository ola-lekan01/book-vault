build:
	@echo "Maven clean install ..."
	mvn clean install
	@echo "Build Completed"

local:
	@echo "Begin Docker Build and Push"
	docker build -t wearecheck_transaction_service:0.0.1 .
	@echo "Docker Build complete ..."


devup:
	@echo "Starting Docker Images..."
	docker compose -f compose.yaml up -d
	@echo "Docker Images Started ..."


devdown:
	@echo "Stopping Docker Images..."
	docker compose -f compose.yaml down
	@echo "Docker Images Stopped ..."