require "test_helper"

class DashboardTest < ActionDispatch::SystemTestCase
  include ActiveJob::TestHelper
  driven_by :rack_test

  test "creating an user" do
    login
    create_user
    assert_match "Usuario añadido.", page.body
  end

  test "searching an user" do
    login
    search_user
    assert_no_match "MyString", page.body
  end

  test "edit an user" do
    login
    edit_user
    assert_match "Name1", page.body
  end

  test "remove an user" do
    login
    remove_user
    assert_no_match "MyString2", page.body
  end

  private

  def remove_user
    visit "/dashboard/create_user"
    find(:xpath, "/html/body/div[2]/div/main/div[2]/table/tbody/tr[1]/td[3]/div/div[2]/form/button").click
  end

  def edit_user
    visit "/dashboard/create_user"
    find(:xpath, "/html/body/div[2]/div/main/div[2]/table/tbody/tr[2]/td[3]/div/div[1]/button").click
    find(:xpath, "/html/body/div[2]/div/main/div[2]/table/tbody/tr[2]/td[3]/div/div[1]/div/div/div/form/div[1]/div[1]/input").fill_in(with: "Name1")
    find(:xpath, "/html/body/div[2]/div/main/div[2]/table/tbody/tr[2]/td[3]/div/div[1]/div/div/div/form/div[2]/input").click
  end

  def search_user(name: "No Record")
    visit "/dashboard/create_user"
    fill_in "search", with: name
    click_button "BUSCAR"
  end

  def create_user(email: "user2@example.com", password: "1234", name: "user")
    assert_difference("User.count") do
      visit "/dashboard/create_user"
      click_button(id: 'addUserButton')
      fill_in "user_name", with: name
      fill_in "user_email", with: email
      fill_in "user_password_digest", with: password
      click_button(id: 'addUserModalButton')
    end
  end

  def login(email: "user2@example.com", password: "secret")
    visit "/login"
    fill_in "login", with: email
    fill_in "password", with: password
    click_on "Entrar"
  end

  def logout
    visit "/logout"
    click_on "Salir"
  end
end
