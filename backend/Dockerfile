FROM ruby:3.3
RUN apt-get update -qq && apt-get install -y build-essential default-mysql-client default-libmysqlclient-dev
RUN apt remove cmdtest -y
RUN curl -sL https://deb.nodesource.com/setup_20.x | bash
RUN apt-get install nodejs
RUN npm install -g npm@latest
RUN npm install -g yarn
WORKDIR /tfg
COPY Gemfile /tfg/Gemfile
COPY Gemfile.lock /tfg/Gemfile.lock
RUN bundle install

COPY entrypoint.sh /usr/bin/
RUN chmod +x /usr/bin/entrypoint.sh
ENTRYPOINT ["entrypoint.sh"]
EXPOSE 80

CMD [ "bundle","exec", "puma", "config.ru"]