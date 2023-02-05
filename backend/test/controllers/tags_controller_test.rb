require "test_helper"

class TagsControllerTest < ActionDispatch::IntegrationTest
  setup do
    login
    @tag = tags(:one)
  end

  # Manually modify the session into what Rodauth expects.
  def login(login: "user@example.com", password: "secret")
    post "/create-account", params: {
      "login"            => login,
      "password"         => password,
      "password-confirm" => password,
    }

    post "/login", params: {
      "floatingInput"    => login,
      "floatingPassword" => password,
    }
  end

  test "should update tag" do
    patch tag_url(@tag), params: { tag: { activity_id: @tag.activity_id, datetime: @tag.datetime, tag: @tag.tag } }
    assert_redirected_to tag_url(@tag)
  end

  test "should destroy tag" do
    assert_difference("Tag.count", -1) do
      delete tag_url(@tag)
    end

    assert_redirected_to tags_url
  end
end
