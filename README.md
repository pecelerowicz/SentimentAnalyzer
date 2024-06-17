# Build data-producer Docker image
cd data-producer
sudo docker build -t data-producer:latest .

# Build analytics Docker image
cd ../analytics
sudo docker build -t analytics:latest .

# Build playground Docker image
cd ../playground
sudo docker build -t playground:latest .

sudo -E API_NEWSAPI_KEY=actual_api_key docker-compose up -d