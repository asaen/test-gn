# Image Manager

## How to build and execute
Execute `mvn spring-boot:run` to start the application. Access its UI client with `http://localhost:8090`.

## Requirements
We have a REST API that manages images. An image has the following attributes:

- id: A unique id
- creation date: The date and time the image was created
- title: A title e.g. "My new shoe"
- tags: A list of tags e.g. Fashion, Shoe.

Implement an endpoint for searching images with specified tags.

- Input: List of 1 or more tags.
- Output: Images that have at least one of the given tags.
