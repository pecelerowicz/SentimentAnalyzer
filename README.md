1. building Jar
maven - clean (in root)
maven - package (in root)


2. building images
# Build data-producer Docker image
cd data-producer
sudo docker build -t data-producer:latest .

# Build analytics Docker image
cd ../analytics
sudo docker build -t analytics:latest .

# Build playground Docker image
cd ../playground
sudo docker build -t playground:latest .

3. starting containers locally (change to actual_api_key to the actual api key)
sudo -E API_NEWSAPI_KEY=actual_api_key docker-compose up -d

4. stopping containers locally
sudo docker-compose down

5. logging to docker hub
docker login   (username is my dockerhub username, then pass token instead of password)

6. tagging
sudo docker tag data-producer:1.0.8 pecelerowicz/data-producer:1.0.8
sudo docker tag analytics:1.0.8 pecelerowicz/analytics:1.0.8
sudo docker tag playground:1.0.8 pecelerowicz/playground:1.0.8

7. pushing
sudo docker push pecelerowicz/data-producer:1.0.8
sudo docker push pecelerowicz/analytics:1.0.8
sudo docker push pecelerowicz/playground:1.0.8