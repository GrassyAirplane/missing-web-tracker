FROM node:latest as build

# Update and install nodejs
RUN apt update
RUN apt install nodejs -y

# Set the working directory
WORKDIR /app

# Add the source code to app
COPY ./ /app/

# Install all the dependencies
RUN npm install

RUN npm run build

FROM nginx:stable-alpine
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html

ENV PORT 80

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]