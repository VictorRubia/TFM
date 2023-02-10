class CreateTagsRepositories < ActiveRecord::Migration[7.0]
  def change
    create_table :tags_repositories do |t|
      t.string :name
      t.string :name_wearos
      t.text :image_data

      t.timestamps
    end
  end
end
