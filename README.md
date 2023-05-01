# Elasticsearch 설치 가이드

## 목차
[1. Elasticsearch 설치를 위한 OS (ubuntu) 설정 [OS (ubuntu) settings for Elasticsearch installation]](#1-elasticsearch-설치를-위한-os-ubuntu-설정-os-ubuntu-settings-for-elasticsearch-installation)  

[2. Elasticsearch 설치 및 클러스터 구성 - Configure Elastic Search and Cluster](#2-elasticsearch-설치-및-클러스터-구성)

-----
# 1. Elasticsearch 설치를 위한 OS (ubuntu) 설정 [OS (ubuntu) settings for Elasticsearch installation]

## 1) **필요 패키지 설치 Install the required package**


>💡  <b>패키지 설치 전 패지키 매니저 업데이트 - Update Manager update before install package installation</b>
>
>’sudo apt-get udpate’, ‘sudo apt-get upgrade’ 명령어를 통해서 패키지 매니저 업데이트
>
>Update package manager through 'sudo apt-get udpate' and 'sudo apt-get upgrade' commands



| package name | description |
| --- | --- |
| net-tools | HOST의 네트워크 정보를 확이하기 위해 설치 
Installation to verify network information for the HOST |
| unzip | 압축 파일 관리를 위한 패키지 설치
Install package for compression file management |
| openssh-server | 서비스 포트 오픈을 위한 패키지 설치
Installing packages to open service ports |
| wget | url을 통한 파일 다운로드를 하기위한 패키지 설치
Install package to download files through lurl |

## 2) **Elasticsearch os(ubuntu) 설정  -  OS settings**

Elasitcsearch 노드를 설치하기 위한 기본 os 설정입니다 ‘ubuntu’ OS 에서는 `/etc/security/limits.conf` 파일에 아래의 내용들을 추가하는 작업을 합니다  
Default os setting for installing Elasitcsearch node The 'ubuntu' OS adds the following contents to the `/etc/security/limits.conf` file

**File descriptor check**

유저에 의해 프로세스 실행 시 가질 수 있는 디스크립터 제한  
Restricted disk assembly that can have been executed

```coffeescript
elastic hard nofile 65536
elastic soft nofile 65536
root hard nofile 65536
root soft nofile 65536
tguser - nofile 65536
```

**Maximum number of threads check**

유저에 의해 실행 할 수 있는 프로세스의 갯수 제한 설정  
Setting a limit on the number of processes that can be run by the user

```coffeescript
elastic hard nproc 65536
elastic soft nproc 65536
root hard nproc 65536
root soft nproc 65536
tguser - nproc 65536
```

**Max file size check**

유저에 의해 실행된 프로세스가 확인할 수 있는 파일 사이즈 제한 설정  
Set File Size restrictions executed by the user

```coffeescript
elastic hard fsize unlimited
elastic soft fsize unlimited
root hard fsize unlimited
root soft fsize unlimited
tguser - fsize unlimited
```

**Maximum size virtual memory check**

유저 당 메모리 제한해제 설정  
Turn off memory limits per user

```coffeescript
elastic hard memlock unlimited
elastic soft memlock unlimited
root hard memlock unlimited
root soft memlock unlimited
tguser - memlock unlimited
```

**'vm.max_map_count` 설정변경**

‘/etc/sysctl.conf’ 파일에의 내용을 아래와 같이 추가하여 줍니다  
Add the contents to the file '/etc/sysctl.conf' as follows

```bash
vm.max_map_count=262144

sudo sysctl -p
```

# 2. Elasticsearch 설치 및 클러스터 구성

## 1) 설치 방안 kinds of install guide

Elasticsearch 설치 방안에 대한 방법 내용입니다

Docker를 통해 설치하는 방법과 일반적으로 설치 관련 tar 파일을 다운로드 받아 실행 시키는 두가지 방법이 존재합니다

How to install Elasticsearch

There are two methods: installing through Docker and downloading and running tar files related to installation in general

<br>

💡 <b>Config File</b>  
>두 가지 설치 방식 모두 ‘elasticsearch.yml’, ‘jvm_options’ 파일에서 설정을 변경한 뒤 반영할 수 있도록 설정 됩니다  
All two installations methods are set to reflect the settings from the 'jvm_options" and “elasticsearch.yml”

<br>

### Docker

Install guide  : [https://www.elastic.co/guide/en/elasticsearch/reference/8.3/docker.html](https://www.elastic.co/guide/en/elasticsearch/reference/8.3/docker.html)

1) docker hub에서 elasticsearch 공식 이미지 다운로드

    Download the official image of elasticsearch from the docker hub

    ```bash
    docker pull docker.elastic.co/elasticsearch/elasticsearch:8.3.3
    ```

2) 아래의 명령어를 통해 docker conatiner 생성

    Use the command below to create a docker conatiner

    ```bash
    docker run -d --name elasticsearch -p 9200:9200 docker.elastic.co/elasticsearch/elasticsearch
    ```
<br>  

### VM (Host OS)

- [Elastic](https://www.elastic.co/kr/downloads/elasticsearch) 공식 다운로드 페이지에서 tar 파일을 다운로드 받습니다 (wget 사용하여 파일링크로 다운로드)  
Download the tar file from the Elastic official download page (use wget to download to file)
    
    ```bash
    wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.3.3-linux-x86_64.tar.gz
    wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-8.3.3-linux-x86_64.tar.gz.sha512
    
    shasum -a 512 -c elasticsearch-8.3.3-linux-x86_64.tar.gz.sha512
    
    tar -xvf elasticsearch-8.3.3-linux-x86_64.tar.gz
    ```
    
- 다운로드 받은 tar 파일을 압축 해제합니다  
Extract the downloaded tar file
    
    ```bash
    tar -xvf elasticsearch-8.3.3-linux-x86_64.tar.gz
    ```
    
- 서비스 실행 전 config 파일을 수정합니다  
Modify the service before the service execution configuration
    
    **디렉토리의 위치는 각 설치 환경마다 다를 수 있습니다!  
    The location of the directory can be different for each installation!**
    
    ```bash
    mv ./config/ ./elasticssearch-8.3.3/config/
    ```
    
- 아래의 명령어를 통해 실행 시킵니다  
Run it through the command below
    
    **압축 해제한 elasticsearch 디렉토리에서 아래와 같이 실행 시킵니다! 
    Run it from the extracted elasticsearch directory as follows!**
    
    ```bash
    ./bin/elasticsearch
    ```
    
<br>  

## 2) single 노드 구성 방법 - Single node configuration method
<br>  

### Docker 기반 설치 -  Install to use Docker**

docker 기반으로 single 노드형태의 elasticsearch 설치는 ‘./docker/single’ 디렉토리 하위에 존재하는 docker-compose 파일을 실행 시킴으로 생성됩니다  

An elasticsearch installation in the form of a single node based on docker is created by running the docker-compose file that exists under the './docker/single' directory

![https://user-images.githubusercontent.com/65060314/229327543-31687518-0c6b-43d2-a176-ea41b9b9b4f3.png](https://user-images.githubusercontent.com/65060314/229327543-31687518-0c6b-43d2-a176-ea41b9b9b4f3.png)

**YAML 파일 내용 - Description YAML file**

```yaml
version: '3.8'
services:
	# Container for creating a directory to connect node data to the host before creating an elasticsearch node
  make-directory:
    image: ubuntu:latest

    volumes:
      - type: bind
        source: ./
        target: /usr/share/
    command: sh -c "mkdir -p /usr/share/data /usr/share/logs"

	# Create elasticsear node container
  elasticsearch_sigle_node:
    image: docker.elastic.co/elasticsearch/elasticsearch:8.3.3
    container_name: es_single_node
    ulimits:
      memlock:
        soft: -1
        hard: -1

    ports:
      - 9350:9200
      - 9450:9300
    volumes:
			# For config configuration information, refer to the root directory reference './config/single/elasticsearch.yml'
      - ../../config/single/elasticsearch.yml:/usr/share/elasticsearch/config/elasticsearch.yml
			# Configuration information refer to root directory reference "./config/config/jvm.optons Single/jvm
      - ../../config/single/jvm.options:/usr/share/elasticsearch/config/jvm.options
			# Mount the data directory created through 'make-directory' in the '/usr/share/elasticsearch/data' directory inside the container
      - ./data:/usr/share/elasticsearch/data
			# Mount logs directory created through 'make-directory' in '/usr/share/elasticsearch/logs' directory inside container
      - ./logs:/usr/share/elasticsearch/logs

    depends_on:
      - make-directory
```

명령어 실행을 통해 컨테이너 생성
Create container through commands

**github에서 clone 한 프로젝트의** **‘./docker/single’ 디렉토리에 이동하여 실행  
Go to './docker/single' directory of project cloned from github and run**

```yaml
docker-compose up -d 
```
<br>  

### VM(Host OS) **기반 설치 - Install to use VM enviroment**

elasticsearch 공식 홈페이지에서 다운로드 받은 설치파일의 ‘./config/elasticsearch.yml’ 파일의 내용을 github에서 clone 받은 ‘./config/single/elasticsearch.yml’의 내용으로 변경하여 실행 시킴으로 구성합니다  

Configure the contents of the './config/elasticsearch.yml' file downloaded from the official website of elasticsearch to the contents of './config/single/elasticsearch.yml' cloned from github

![https://user-images.githubusercontent.com/65060314/229328010-55920fec-d360-482f-9170-a72b893fcdbe.png](https://user-images.githubusercontent.com/65060314/229328010-55920fec-d360-482f-9170-a72b893fcdbe.png)

**아래의 명렁어를 실행하여 서비스 시작  
Start the service by running the following Myeonglung word**

**elasticsearch 공식 홈페이지에서 다운로드 받은  디렉토리에 이동하여 실행  
Navigate to and run the downloaded directory from the official website of elasticsearch**

```yaml
./bin/elasticsearch
```
<br>  

## 2) 클러스터 구성 방법 (2개 이상의 노드 구성) - Cluster configuration method (two or more nodes configured)

elasticsearch는 분산 노드 형태로 구성되어 서비스되어지는 것이 일반적입니다 아래의 내용은 docker container, vm 기반의 환경에서  클러스터 구성방법에 대한 내용입니다  

Elasticsearch is typically serviced in the form of distributed nodes Below is how to configure a cluster in a docker container, vm-based environment
<br>  

### D**ocker 기반 설치 - Install to use Docker**

컨테이너는 단일 OS 상에서 커널을 격리하여 각각의 서비스를 제공합니다  아래의 내용은 **같은 Host os상에서 컨테이너를 통해 elasticsearch 노드를 생성하여 클러스터를 구축하는 방법**과 **분산 컴퓨팅 환경(서로 다른 host os)에서 컨테이너를 통해서 격리 후 클러스터를 구축하는 방법**입니다  

Containers provide their respective services by isolating the kernel on a single OS Below is how to build a cluster by creating an elasticsearch node through a container on the same hostos and how to build a cluster after isolation through a container in a distributed computing environment (different hosts)

![같은 Host OS 상에서 컨테이너로 클러스터 구성 -Configuring cluster with container on the same host OS](https://user-images.githubusercontent.com/65060314/229339614-cbfce9df-6c3b-4ad8-9526-d8490f9b676c.png)

같은 Host OS 상에서 컨테이너로 클러스터 구성 - Configuring cluster with container on the same host OS

![서로 다른 Host OS 상에서 컨테이너로 클러스터 구성 - Configuring cluster configuration to containers on other hosts](https://user-images.githubusercontent.com/65060314/229339732-9089d4da-cdef-497a-a1af-bddb70fad946.png)

서로 다른 Host OS 상에서 컨테이너로 클러스터 구성 - Configuring cluster configuration to containers on other hosts

<br>  

**같은 Host os 에서 클러스터 구축하기 - Building a Cluster on the Same Hostos**

- 설치 관련 파일 설명 - Description related file description
    
    config file (./config/cluster/same_host_os)
    
    ![https://user-images.githubusercontent.com/65060314/229338526-6c052a5e-9ae9-4e62-925a-31ddf50abd5e.png](https://user-images.githubusercontent.com/65060314/229338526-6c052a5e-9ae9-4e62-925a-31ddf50abd5e.png)
    
    docker-compose file (./docker/cluster/same_host_os)
    
    YAML file 상세내용은 주석 참고
    
    ![https://user-images.githubusercontent.com/65060314/229338721-296be910-89be-456b-b248-1e34a8e02916.png](https://user-images.githubusercontent.com/65060314/229338721-296be910-89be-456b-b248-1e34a8e02916.png)
    
<br>  


- **cluster 컨테이너 생성과정 - Cluster container creation process**
    
    1) ‘. /docker/cluster/same_host_os’ 디렉토리로 이동하여 ‘docker-compose’를 통해 컨테이너 생성하기
    
        Go to /docker/cluster/same_host_os' directory and create containers through 'docker-compose’
        
        ```bash
        # 디렉토리로 이동
        cd ./docker/cluster/same_host_os
        # docker-compose 명령어를 통해 실행
        docker-compose up -d
        ```
    

    2) elasticsearch user 비밀번호 설정 - Set elasticsear user password

        아래의 명령어를 master 컨테이너 내부로 접근하여 비밀번호를 초기화합니다

        Access the command below into the master container to initialize the password

        ```bash
        # 컨테이너 내부로 접근
        docker exec -it master /bin/bash
        # elasticsearch user 비밀번호 초기화
        ./bin/elasticsearch-setup-passwords interactive -url https://localhost:9350
        ```

    3) cluster가 정상적으로 구축되었는지 확인 - Verify that the cluster is deployed successfully

        아래의 api를 호출하여 정상 적으로 노드가 서비스되고 있는지 확인  
        Call the api below to verify that the node is being serviced normally

        ```bash
        https://localhost:9350/_cat/nodes
        ```

    4) kibana 정상 작동 확인 - Confirm normal operation of kibana


        >💡 <b>kibana - elasticsearch auth 정보 kibana - elasticsearch auth information </b>
        >
        >해당 과정에서 지정한 비밀번호를 kibana 컨테이너와 elasticsearch master 노드를 연동하기위해 kibana.yml 파일을 수정해 주어야합니다
        ./config/cluster/same_host_os/kibana/kbiana.yml 파일에서 `elasticsearch.username`,`elasticsearch.password` 를 수정해주어야합니다
        변경 후 kibana 컨테이너를 재시작합니다
        >
        >The kibana.yml file must be modified to link the specified password with the elasticsearch master node
        ./config/cluster/same_host_os/kibana/kbiana.yml 파일에서 elasticsearch.username,elasticsearch.You need to modify the password
        Restart the kibana container after the change
        >
        >![https://user-images.githubusercontent.com/65060314/229339203-21a4a1c6-92d5-46fd-96a8-e7b5cf10da9c.png](https://user-images.githubusercontent.com/65060314/229339203-21a4a1c6-92d5-46fd-96a8-e7b5cf10da9c.png)

<br>  

**분산 컴퓨팅 환경에서 컨테이너를 통해 구축하기 - Deploy from a container in a distributed computing environment**


>💡 <b>네트워크는 호스트의 정보를 따라갑니다 - The network follows the host's information<b>  
>해당 내용에서 elasticsearch는 컨테이너로 격리는되어지지만 네트워크는 호스트의 네트워크를 그대로 전달 받아 구축되어집니다 그 이유는 enrollment token을 통해 클러스터가 구축되어 질 때 Host 네트워크가 아닐 경우 보안설정 자동화에서 에러가 발생합니다
>
>In this context, elasticsearch is isolated to containers, but the network is built by communicating the host's network as it is because when a cluster is built through the enrollment token, if it is not a host network, the security settings automation fails

<br>

 **cluster 컨테이너 생성과정 - Cluster Container Creation Process**

1) master(Dedicate) 노드 컨테이너 실행시키기 - Run master (Dedicate) node container

    master(Dedicate) 노드를 생성하기 위해 VM에서 컨테이너를 실행합니다 아래의 명령어를 통해 진행합니다
    Run containers on VMs to create master (Dedicate) nodes Use the command below to proceed

    ```bash
    # 디렉토리 이동 (프로젝트 디렉토리 기준)
    cd ./docker/cluster/other_host_os
    # docker 컨테이너 생성
    docker-compose -f master.yml up -d
    ```

2) elasticsearch auth 정보 변경 - changing elasticsearch auth information
master(Dedicate) 노드 컨테이너 내부로 접근하여 아래의 명령어를 통해 각 user 별 비밀번호를 변경합니다

    Access to each m node container internal and change the password below

    ```bash
    docker exec -it master /bin/bash

    ./bin/elasticsearch-setup
    ```

3) enrollment token 발급 - Create enrollment token

    master(Dedicate) 노드 컨테이너 내부로 접근하여 아래의 명령어로 토큰 발급 → 발급한 토큰은 노드 추가 시 사용 ( ‘3)data 노E드 추가를 위한 ‘.env’파일의 `ENROLLMENT_TOKEN` 값 추가’ 항목 참조)

    Access the master (Dedicate) node container and create a token with the command below → Use the token created when adding a node (see the topic '3)Add ENROLLMENT_TOKEN value' in the '.env' file for adding a data node.)

    ```bash
    docker exec -it master /bin/bash
    ./bin/elasticsearch-create-enrollment-token -s node
    ```

4) data 노드 추가를 위한 ‘.env’파일의 `ENROLLMENT_TOKEN` 값 추가 - Add ENROLLEMENT_TOKEN value in file '.env' for adding data nodes

    데이터 노드로 추가할 VM에서 프로젝트를 클론 받은 뒤  ‘./docker/cluster/other_host_os’ 로 이동하여
    2번 항목에서 발급한 토큰의 값을 .env 파일의 `ENROLLMENT_TOKEN` 에 추가합니다

    Copy the project from the VM you want to add as a data node and go to './docker/cluster/other_host_os'
    Add the value of the token issued in item 2 to ENROLLEMENT_TOKEN in the .env file

    ![https://user-images.githubusercontent.com/65060314/229431348-e7e337a7-ac68-4189-8dd4-a8c433c284be.png](https://user-images.githubusercontent.com/65060314/229431348-e7e337a7-ac68-4189-8dd4-a8c433c284be.png)

5) data 노드 실행 - Run data node

    ```bash
    # 디렉토리 이동 (프로젝트 디렉토리 기준)
    cd ./docker/cluster/other_host_os
    # data 1 실행
    docker-compose -f data1.yml up -d
    ```

6) 정상작동 확인 - Check normal operation 

    명령어로 확인 -  Confirm with command

    ```bash
    {master 노드 host 주소}/_cat/nodes
    ```

    ![Untitled](https://user-images.githubusercontent.com/65060314/229438779-d39488fe-b7a4-4a6b-88ce-2f26829c5e8f.png)


    >💡 <b>enrollment token을 활용한 kibana 연동 방법- Kibana Interworking Method Using Enrollment Token</b>  
    >docker를 통해서 kibana 컨테이너를 생성하거나 tar 파일의 압축을 해제하여 실행 시키면 아래와 같은 화면이 출력됩니다
    >
    >1) elasticsearch master 노드에서 ‘./bin/elasticsearch-create-enrollment-token -s kibana’ 명령을 통해 토큰을 발급받아 입력합니다
    >
    >2) kibana 서비스 로그에 남겨진 식별 코드를 입력하여 연동을 완료합니다
    >
    >If you create a kibana container through the 💡 docker or unzip the tar file and run it, the following screen will be displayed
    >
    >1) From the elasticsearch master node, issue and enter the token via the command './bin/elasticsearch-create-enrollment-token-skibana'
    >
    >2) Complete the interworking by entering the identification code left in the kibana service log
    ![image](https://user-images.githubusercontent.com/65060314/235395017-f2ba1120-4af7-47b2-a87e-b3ba8981080b.png)

