## Deploy Elasticsearch with docker

This repository is defined **to deploy Elasticsearch using Docker containers** within a dynamic infrastructure environment.

### Deployment structs

This repository supports three deployment methods:

- **Single Node**
    
    ![single_node](https://github.com/cucuridas/elasticsearch_deploy/assets/65060314/79ea54cb-ca19-4f19-aeb8-364c078ebb6d)
    
    Deploys Elasticsearch as a single-node service using containers.
    
- **Master - Slave**
    
    ![cluaster_arch](https://github.com/cucuridas/elasticsearch_deploy/assets/65060314/975cc08d-e7be-499d-a797-1acb560b6c50)
    
    Consists of two nodes, each deployed as Dedicated (with all Node.roles).
    
- **RAFT Cluster**
    
    ![raft_cluaster](https://github.com/cucuridas/elasticsearch_deploy/assets/65060314/1cc948e6-2d6b-42f0-9968-f977399c0211)
    
    Comprises a total of 5 Elasticsearch nodes: 2 Master nodes and 3 Data nodes. Data nodes are configured with `voting_only` to support a quorum-based master election mechanism.
    

### Important Notes and References

- **Requires Docker Engine to be installed**
- **OS configurations** are necessary to support Elasticsearch services.
- Configuration for each node can be modified in the `./config` directory.
- TLS settings are **not applied** for quick deployments.