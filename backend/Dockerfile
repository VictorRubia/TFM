# syntax=docker/dockerfile:1
FROM ruby:3.3
RUN apt-get update -qq && apt-get install -y build-essential default-mysql-client default-libmysqlclient-dev pip
RUN apt remove cmdtest -y
RUN pip install --break-system-packages lightgbm matplotlib scikit-learn==1.4.2 sklearn-pandas
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash
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
EXPOSE 80

# Configure the main process to run when running the image
#CMD ["rails","s","-b","0.0.0.0","-p","3000"]
CMD [ "bundle","exec", "puma", "config.ru"]
# CMD ["rails","server"] # you can also write like this.