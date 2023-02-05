class User::PrivateApiKeysController < ApplicationController

  before_action :authenticate

  def update
    puts params.inspect
    if User.find(params[:user][:id]).update(private_api_key: SecureRandom.hex)
      redirect_to dashboard_create_user_path, notice: "Clave API actualizada"
    else
      redirect_to dashboard_create_user_path, alert: "Ha habido un error: #{current_user.errors.full_messages.to_sentence}"
    end
  end
end
