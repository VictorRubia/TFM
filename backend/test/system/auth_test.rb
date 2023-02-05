require "test_helper"

class AuthenticationTest < ActionDispatch::SystemTestCase
  include ActiveJob::TestHelper
  driven_by :rack_test

  test "creating an account" do
    create_account
    assert_match "An email has been sent to you with a link to verify your account", page.html
  end

  test "logging in and logging out" do
    create_account

    login
    assert_match "You have been logged in", page.body

    logout
    assert_match "You have been logged out", page.body

  end

  private

  def create_account(email: "user2@example.com", password: "secret")
    visit "/create-account"
    find("#login").fill_in(with: email)
    find("#password").fill_in(with: password)
    find("#password-confirm").fill_in(with: password)
    click_on("buttonRegister")
  end

  def login(email: "user2@example.com", password: "secret")
    visit "/login"
    fill_in "login", with: email
    fill_in "password", with: password
    click_button "Entrar"
  end

  def logout
    visit "/logout"
    click_on "Salir"
  end
end
