class CreateTags < ActiveRecord::Migration[7.0]
  def change
    create_table :tags do |t|
      t.json :tag
      t.datetime :datetime
      t.references :activity, null: false, foreign_key: true

      t.timestamps
    end
  end
end
