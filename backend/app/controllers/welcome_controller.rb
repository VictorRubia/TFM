class WelcomeController < ApplicationController
  def index
  end

  def download_wearos_apk
    send_file("#{Rails.root}/public/wear.apk")
  end

  def download_android_apk
    send_file("#{Rails.root}/public/android.apk")
  end
end
