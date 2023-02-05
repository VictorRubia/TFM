class Api::V1::UsersController < ApplicationController
  before_action :test_password, only: [:index,:get_api_key,:show]

  # GET /users
  def index
    @users = User.find_by( email: params[:email])
    render json: @users
    @user.requests.create(method: :get, requestable_type: "User")
  end

  def get_api_key
    @users = User.find_by( email: params[:email])
    render json: {
      user_details: @users, api_key: @users.private_api_key
    }
  end

  def post_password_recovery
    @users = User.find_by( email: params[:email])
    if !@users.nil?
      PersonMailer.with(person: @users).password_recovery.deliver_later
      render json: {message: 'Solicitud procesada'}, status: 200
    else
      render json: {message: 'Usuario no encontrado'}, status: 404
    end
  end

  # GET  /users/:id
  def show
    @users = User.find(params[:id])
    render json: @users
    @user.requests.create(method: :get, requestable_type: "User")
  end

  # POST /users
  def create
    @users = User.new(user_params)
    if @users.save
      render json: @users
    else
      render error: { error: 'Unable to create User.' }, status: 400
    end
    @user.requests.create(method: :post, requestable_type: "User")
  end
  # PUT /users/:id
  def update
    @users = User.find(params[:id])
    if @users
      @users.update(user_params)
      render json: { message: 'User successfully update. '}, status:200
    else
      render json: { error: 'Unable to update user. '}, status:400
    end
    @user.requests.create(method: :put, requestable_type: "User")
  end
  # DELETE /users/:id
  def destroy
    @users = User.find(params[:id])
    if @users
      @users.destroy
      render json: { message: 'User successfully deleted. '}, status: 200
    else
      render json: { error: 'Unable to delete User. '}, status: 400
    end
    @user.requests.create(method: :delete, requestable_type: "User")
  end
  private
  def user_params
    params.require(:user).permit(:name, :email, :password_digest)
  end

  def test_password
    authenticate_with_http_token do |token, options|
      @user ||= User.find_by(private_api_key: token)
    end
    @users = User.find_by(email: params[:email])
    if !@users.nil?
      render json: { message: "No autorizado" }, status: :unauthorized unless @users.password_digest == params[:password_digest]
    elsif !@user.nil?
      render json: @user, status: 200
    else
      render json: { message: "No autorizado" }, status: :unauthorized
    end
  end
end
