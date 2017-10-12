#
# Be sure to run `pod lib lint NPFramework.podspec' to ensure this is a
# valid spec before submitting.
#
# Any lines starting with a # are optional, but their use is encouraged
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html
#

Pod::Spec.new do |s|
  s.name             = 'NPFramework'
  s.version          = '0.1.0'
  s.summary          = 'NPFramework for mobile apps to communicate with NP API.'

# This description is used to generate tags and improve search results.
#   * Think: What does it do? Why did you write it? What is the focus?
#   * Try to keep it short, snappy and to the point.
#   * Write the description between the DESC delimiters below.
#   * Finally, don't worry about the indent, CocoaPods strips it!

  s.description      = <<-DESC
This pod will be used as a Framework to communicate between the NP API and the application running this pod
                       DESC

  s.homepage         = 'https://github.com/robbythedude/Code-Jam-Fall-2017/NPFramework'
  # s.screenshots     = 'www.example.com/screenshots_1', 'www.example.com/screenshots_2'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'Robert Steiner' => 'brogrammerrob@gmail.com' }
  s.source           = { :git => 'https://github.com/Robert Steiner/NPFramework.git', :tag => s.version.to_s }
  # s.social_media_url = 'https://twitter.com/<TWITTER_USERNAME>'

  s.ios.deployment_target = '8.0'

  s.source_files = 'NPFramework/Classes/**/*'
  
  # s.resource_bundles = {
  #   'NPFramework' => ['NPFramework/Assets/*.png']
  # }

  # s.public_header_files = 'Pod/Classes/**/*.h'
  # s.frameworks = 'UIKit', 'MapKit'
  # s.dependency 'AFNetworking', '~> 2.3'
end
