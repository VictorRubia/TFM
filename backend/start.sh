# start.sh
if [[ $DYNO == "web"* ]]; then
  bundle exec rails server -b 0.0.0.0 -p $PORT -e production