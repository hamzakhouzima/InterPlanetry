# Use an official Ubuntu as a base image
FROM ubuntu:latest


RUN  add-apt-repository ppa:ipfs/ipfs

# Install IPFS dependencies
RUN apt-get update && apt-get install -y ipfs

# Expose necessary ports for IPFS
EXPOSE 4001
EXPOSE 5001
EXPOSE 8080

# Set the default command to run when the container starts
CMD ["ipfs", "daemon"]

#TODO : get the ipfs node image from docker hub and intergrate it in the dockerfile of the doctor service
