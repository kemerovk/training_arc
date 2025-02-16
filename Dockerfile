FROM ubuntu:latest
LABEL authors="tsoyk"

ENTRYPOINT ["top", "-b"]