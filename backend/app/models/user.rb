class User < ApplicationRecord
  include ActiveModel::SecurePassword
  has_secure_password
  encrypts :private_api_key
  blind_index :private_api_key

  before_create :set_private_api_key

  validates :private_api_key, uniqueness: true, allow_blank: true

  belongs_to :account
  has_many :activities, dependent: :destroy
  has_many :requests, dependent: :destroy
  has_one :user_stress, dependent: :destroy

  validates :name, :email, presence: true
  validates :email, uniqueness: true

  def self.search(search)
    if search
      user = User.where("name like ?", "%#{search}%")
      if user
        self.where(id: user)
      else
        User.all
      end
    else
      User.all
    end
  end

  private
  def set_private_api_key
    self.private_api_key = SecureRandom.hex if self.private_api_key.nil?
  end
end
