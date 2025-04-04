require_relative "boot"

require "rails/all"

# Require the gems listed in Gemfile, including any gems
# you've limited to :test, :development, or :production.
Bundler.require(*Rails.groups)

module Tfg
  class Application < Rails::Application
    # Initialize configuration defaults for originally generated Rails version.
    config.load_defaults 7.0

    config.i18n.default_locale = 'es-ES'

    config.action_controller.include_all_helpers = true

    # Configuration for the application, engines, and railties goes here.
    #
    # These settings can be overridden in specific environments using the files
    # in config/environments, which are processed later.
    #
    config.time_zone = "Europe/Madrid"
    # config.eager_load_paths << Rails.root.join("extras")
    # config.web_console.permissions = '172.18.0.1'
    config.action_mailer.asset_host = 'http://tfm.victorrubia.com'
    config.action_mailer.asset_host = config.action_controller.asset_host
  end
end
