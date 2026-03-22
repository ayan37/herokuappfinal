@fileUpload
Feature::Verify File Upload Functionality
	Background:
		Given I am on Homepage
	@TC001
	Scenario Outline: Verify File is uploaded successfully
		Given I am on "FileUpload" page
		When I select the file with id "<ID>" to upload
		And I upload the file
		Then the file should be uploaded successfully
	Examples:
		|ID|
		|1|
		|2|
		|3|