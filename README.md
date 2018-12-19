# Chord
# This is a project from CSC344 course, Computer Networks.
# A scalable peer-to-peer lookup service that looks up the correct value for a client. In the implementation, the client is a node within a group of nodes and the correct value is the correct key that corresponds to its node. Each node has its own corresponding set of key(s). A finger table is created for each node and the visiting order of nodes shows the order of nodes that the client node jumps to in order to find the node corresponding to the key being looked up. The key being looked up is an IP address.
# Given scenario requirements, create the visiting order of nodes and their finger tables:
# Node ID spaces in which 2^B represents the maximum node space available, for 5 <= B <= 10
# N number of nodes created from 5 <= N <= 15 (N is the number of servers)
# User inputs B and N to begin the program
# The program will randomly decide node IDs from 1 to 2^B and create a finger table for each node
# The program will randomly create one key ID from 1 to 2^B
# The program will choose a node with the second smallest node ID to create the starting node
# Given starting node and created key ID, the program will display the visiting order of nodes and their finger tables
