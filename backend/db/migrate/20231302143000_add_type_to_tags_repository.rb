class AddTypeToTagsRepository < ActiveRecord::Migration[7.0]
  # type:
  # 1 -> estados
  # 2 -> contextos
  # 3 -> emociones
  def change
    add_column :tags_repositories, :type, :integer, :default => false
  end
end