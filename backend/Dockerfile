# syntax=docker/dockerfile:1
FROM ruby:3.1.3
RUN apt-get update -qq && apt-get install -y build-essential default-mysql-client default-libmysqlclient-dev pip
RUN apt remove cmdtest -y
RUN python3 -m pip install pandas matplotlib
RUN python3 -m pip install -U scikit-learn
RUN curl -sL https://deb.nodesource.com/setup_14.x | bash
RUN apt-get install nodejs
RUN npm install -g npm@latest
RUN npm install -g yarn
WORKDIR /tfg
COPY Gemfile /tfg/Gemfile
COPY Gemfile.lock /tfg/Gemfile.lock
RUN bundle install

# Add a script to be executed every time the container starts.
COPY entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]
EXPOSE 3000

# Configure the main process to run when running the image
CMD ["bundle","exec: ","rails: ","server: ","-b: ","0.0.0.0: ","-p: ","$PORT: ","-e: ","production"]