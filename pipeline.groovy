pipeline {
    agent any 
    stages {
        stage('Enter the information of the server you want to distribute'){
            steps {
                script {
                    user = input message: 'Enter the host of the server to deploy', parameters: [string(defaultValue: 'root', name: 'USER')]
                    host = input message: 'Enter the host of the server to deploy', parameters: [string(defaultValue: 'localhost', name: 'HOST')]
                    pub_key = input message: 'Enter the pub_key of the server you want to deploy, or you can move on', parameters: [string(defaultValue: '', name: 'PUB_KEY')]
                }
            }
        }
        stage("Verify server connection with the information entered"){
            steps {
                script {
                    try{
                        if (pub_key) {
                        command = "ssh -i $pub_key $user@$host"
                        value = sh(script: "$command" )
                        println(value)
                        } else {
                            command = "ssh $user@$host"
                            value = sh(script: "$command")
                            println(value)
                        }
                    }catch (e) {
                        catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE')
                        {
                            sh "exit 0"
                        }
                        println("As this is the server for demo, go through this step")
                    }
                    
                }
            }
        }
        stage('GitHub Repository Clone') { 
            steps {
                git branch: 'main', credentialsId: 'github-elasticsearch', url: 'https://github.com/cucuridas/elasticsearch_deploy.git'
            }
        }
        stage("Input sturct to deploy elasticsearch") {
            steps {
                script {
                    struct_value = input message: 'Please select cluster structure of elasticsearch to deploy', parameters: [choice(choices: ['single', 'master-slave', 'raft-cluster'], name: 'DEPLOY_STRUCT')]
                    println(struct_value)
                }
                
            }
        }
        stage("Deploy elasticsearch cluster") {
            steps {
                script {
                    sh (script: "docker ps")
                }
            }
        }
    }
}