class CreateStresses < ActiveRecord::Migration[7.0]
  def change
    create_table :stresses do |t|
      t.datetime :datetime
      t.string :level
      t.references :activity, null: false, foreign_key: true

      t.timestamps
    end
  end
end
