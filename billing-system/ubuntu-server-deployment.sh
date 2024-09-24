# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "$VERSION_CODENAME") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update
sudo apt-get install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo docker run hello-world
curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube && rm minikube-linux-amd64
sudo minikube start
sudo docker pull mrudulaa94/billing_server_repo
curl -LO "https://storage.googleapis.com/kubernetes-release/release/$(curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x ./kubectl
sudo mv ./kubectl /usr/local/bin/kubectl
kubectl version --client
sudo usermod -aG docker $USER
sudo apt update
sudo apt install -y apt-transport-https ca-certificates curl software-properties-common
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo apt-get install qemu-kvm libvirt-daemon-system libvirt-clients bridge-utils
# minikube using driver 
minikube start --driver=kvm2
minikube logs
sudo usermod -aG docker $USER
newgrp docker
minikube start
minikube delete # need we need minikube  to be independent of docker not running minikube in docker
sudo apt-get update
# PACKAGES MISSING FOR MINIKUBE TO RUN WITHOUT DRIVER conntract crictl
sudo apt-get install -y conntrack 
wget https://github.com/kubernetes-sigs/cri-tools/releases/download/v1.26.0/crictl-v1.26.0-linux-amd64.tar.gz
sudo tar zxvf crictl-v1.26.0-linux-amd64.tar.gz -C /usr/local/bin
crictl --version
wget https://github.com/Mirantis/cri-dockerd/releases/download/v0.2.0/cri-dockerd-v0.2.0-linux-amd64.tar.gz
tar xvf cri-dockerd-v0.2.0-linux-amd64.tar.gz
sudo mv ./cri-dockerd /usr/local/bin/ 
cri-dockerd --help
 wget https://raw.githubusercontent.com/Mirantis/cri-dockerd/master/packaging/systemd/cri-docker.service
 wget https://raw.githubusercontent.com/Mirantis/cri-dockerd/master/packaging/systemd/cri-docker.socket
 sudo mv cri-docker.socket cri-docker.service /etc/systemd/system/
 sudo sed -i -e 's,/usr/bin/cri-dockerd,/usr/local/bin/cri-dockerd,' /etc/systemd/system/cri-docker.service
  sudo systemctl daemon-reload
 sudo systemctl enable cri-docker.service
 sudo systemctl enable --now cri-docker.socket
sudo systemctl status cri-docker.socket
# DELETE ALL CONTAINERS
docker system prune
minikube delete
