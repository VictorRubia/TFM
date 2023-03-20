Rails.application.routes.draw do
  resources :activity_tags_assignations
  resources :activity_assignations
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
    get '/dashboard/user/:id/activity_assignations' => 'dashboard#activity_assignation', as: :dashboard_activity_assignation
    post '/dashboard/user/:id/activity_assignations' => 'dashboard#activity_assign'
    delete '/dashboard/user/:id/activity_assignations' => 'dashboard#activity_unassign'
    get '/dashboard/create_user/search' => 'dashboard#search_user', as: :dashboard_search_user
    post '/dashboard/create_user', to: 'users#create'
    get '/dashboard/activity_user' => 'dashboard#activity_user', as: :dashboard_activity_user
    get '/dashboard/activity_user/search' => 'dashboard#search_user_activities', as: :dashboard_search_user_activity
    get '/dashboard/tags/search' => 'dashboard#search_tags', as: :dashboard_search_tags
    get '/dashboard/activity_repo/search' => 'dashboard#search_activity_repo', as: :dashboard_search_activity_repo
    get '/dashboard/activity_user/:id' => 'dashboard#view_activities', as: :dashboard_view_activity
    get '/dashboard/activity_user/:id/:id_activity' => 'dashboard#activity_details', as: :dashboard_activity_details
    get '/dashboard/activity_user/:id/:id_activity/reprocess' => 'activities#reprocess', as: :activity_reprocess
    get 'dashboard/tags' => 'dashboard#tags', as: :dashboard_tags
    get 'dashboard/activity_repo' => 'dashboard#activity_repo', as: :dashboard_activity_repo
    get 'dashboard/activity_repo/config/:id' => 'dashboard#configure_activity_repo', as: :dashboard_settings_activity_repo
    post 'dashboard/activity_repo/config/:id' => 'dashboard#configure_activity_repo_post', as: :dashboard_settings_activity_repo_post
    resources :ppg_measures
    resources :accelerometer_measures
    resources :gps_measures
    resources :users
    resources :tags
    resources :tags_repositories
    resources :activities_repositories
    get 'pruebas', to: 'welcome#pruebas'
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
        resources :tags_repositories, only: [:index]
        resources :activities_repositories, only: [:index]
      end
    end
  end

end
