class CreateUserStresses < ActiveRecord::Migration[7.2]
  def change
    create_table :user_stresses do |t|
      t.float :level, null: false
      t.references :user, null: false, foreign_key: true

      t.timestamps
    end
  end
end
