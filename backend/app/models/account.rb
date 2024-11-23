class Account < ApplicationRecord
  include Rodauth::Rails.model

  has_many :users, dependent: :destroy
end
