build:
	@echo "Creating SQS queue..."
	awslocal sqs create-queue --queue-name my-queue

	@echo "Creating SNS topic..."
	awslocal sns create-topic --name my-topic

	@echo "Subscribing SQS queue to SNS topic..."
	awslocal sns subscribe \
		--topic-arn "arn:aws:sns:us-east-1:000000000000:my-topic"  \
		--protocol sqs \
		--notification-endpoint "arn:aws:sqs:us-east-1:000000000000:my-queue"

	@echo "Creating Lambda function..."
	awslocal lambda create-function \
		--function-name my-lambda \
		--runtime nodejs18.x \
		--zip-file fileb://mylambda.zip \
		--handler index.handler \
		--role arn:aws:iam::000000000000:role/lambda-role

	@echo "Creating event source mapping for Lambda function..."
	awslocal lambda create-event-source-mapping \
		--function-name my-lambda \
		--batch-size 5 \
		--maximum-batching-window-in-seconds 60 \
		--event-source-arn arn:aws:sqs:us-east-1:000000000000:my-queue

	@echo "Creating S3 bucket..."
	awslocal s3 mb s3://anotaai-catalog-marketplace

	@echo "Build completed."


create_lambda:
	@echo "Creating Lambda function..."
	awslocal lambda create-function \
		--function-name my-lambda \
		--runtime nodejs18.x \
		--zip-file fileb://mylambda.zip \
		--handler index.handler \
		--role arn:aws:iam::000000000000:role/lambda-role