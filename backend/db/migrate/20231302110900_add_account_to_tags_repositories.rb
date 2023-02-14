class AddAccountToTagsRepositories < ActiveRecord::Migration[7.0]
  def change
    add_reference :tags_repositories, :account, null: false, foreign_key: true
  end
end
