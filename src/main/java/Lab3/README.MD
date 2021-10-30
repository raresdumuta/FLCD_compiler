##### Symbol Table
    Is a hash table that is using linear probing for collisions.

##### Codification
    Uses a hash map that stores pairs of the form String, Integer. 
    Each value has its unique code. The firs key is 0 that is used for 
    identiers, 1 for constants.

##### MyScanner class
    The algorithm used for scanning splits the whole file in lines
    then the line are splitted into tokens, for each token it looks
    up for the position in Symbol Table, if its an operator, separator
    or a reserved word their position will be -1.


    