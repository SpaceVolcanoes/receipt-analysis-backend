# AWS Server 

- Our public ip: 13.53.140.53
- Our private ip: 172.31.8.59

## Accesing the server:

#### You can get into the server with the SV.pem keys file by:
- SV.pem file is in the root folder right now
`ssh -i SV.pem ubuntu@13.53.140.53`

#### But if you have your own rsa public key then you can copy it to the server by executing:
`cat .ssh/id_rsa.pub | ssh -i SV.pem ubuntu@13.53.140.53 "cat >> .ssh/authorized_keys"`
*it's kind of a hack but we can't use ssh-copy-id because pem files are weird*

#### Then you can access the server simply by:
`ssh ubuntu@13.53.140.53`