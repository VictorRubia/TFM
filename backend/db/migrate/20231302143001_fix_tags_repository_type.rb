class FixTagsRepositoryType < ActiveRecord::Migration[7.0]
  def change
    rename_column :tags_repositories, :type, :tags_type
  end
end
