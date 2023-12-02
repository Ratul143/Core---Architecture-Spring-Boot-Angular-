# Use the official Node.js 14.17.0 image as a parent image
FROM node:14.17.0 AS builder

# Set the working directory
WORKDIR /app

# Copy the package.json and package-lock.json files to the working directory
COPY package*.json ./

# Install the app's dependencies
RUN npm install

# Copy the rest of the app's files to the working directory
COPY . .

# Build the app
RUN npm run build --prod

# Use the official Apache Tomcat 8.5.82 image as a parent image
FROM tomcat:8.5.82

# Copy the compiled app files to the Tomcat webapps directory
COPY --from=builder /app/dist/* E:/DAL_Production_Demo/webapps/

# Set the Tomcat HTTP port to 9001 (the port the server is running on)
EXPOSE 9001

# Start Tomcat
CMD ["catalina.sh", "run"]
