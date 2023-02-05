Rails.application.routes.draw do
  root "welcome#index"
  get 'activities/:id/export', to: 'activities#export'
  get 'activities/:id/ppg_measures', to: 'activities#ppg_measures'
  get 'activities/:id/accelerometer_measures', to: 'activities#accelerometer_measures'
  get 'download_wearos_apk', to: 'welcome#download_wearos_apk'
  get 'download_android_apk', to: 'welcome#download_android_apk'
  resources :activities
  constraints Rodauth::Rails.authenticated do
    get '/dashboard/index', to: 'dashboard#activity_user'
    get '/dashboard', to: 'dashboard#activity_user'
    get '/dashboard/create_user' => 'dashboard#create_user', as: :dashboard_create_user
    get '/dashboard/create_user/search' => 'dashboard#search_user', as: :dashboard_search_user
    post '/dashboard/create_user', to: 'users#create'
    get '/dashboard/activity_user' => 'dashboard#activity_user', as: :dashboard_activity_user
    get '/dashboard/activity_user/search' => 'dashboard#search_user_activities', as: :dashboard_search_user_activity
    get '/dashboard/activity_user/:id' => 'dashboard#view_activities', as: :dashboard_view_activity
    get '/dashboard/activity_user/:id/:id_activity' => 'dashboard#activity_details', as: :dashboard_activity_details
    get '/dashboard/activity_user/:id/:id_activity/reprocess' => 'activities#reprocess', as: :activity_reprocess
    resources :ppg_measures
    resources :accelerometer_measures
    resources :gps_measures
    resources :users
    resources :tags
  end

  get '/welcome/index'
  namespace :user do
    resource :private_api_keys, only: :update
  end

  namespace :api do
    namespace :v1 do
      defaults format: :json do
        resources :users, only: [:index]
        get 'users/get_api_key', to: 'users#get_api_key'
        get 'users/password_recovery', to: 'users#post_password_recovery'
        resources :measures, only: [:index, :create, :show, :update, :destroy]
        resources :activities, only: [:index, :create, :show, :update, :destroy]
        resources :ppg_measures, only: [:index, :create, :show, :update, :destroy]
        resources :gps_measures, only: [:index, :create, :show, :update, :destroy]
        resources :accelerometer_measures, only: [:index, :create, :show, :update, :destroy]
        resources :step_measures, only: [:index, :create, :show, :update, :destroy]
        resources :significant_mov_measures, only: [:index, :create, :show, :update, :destroy]
        resources :tags, only: [:index, :create, :show, :update, :destroy]
      end
    end
  end

end
