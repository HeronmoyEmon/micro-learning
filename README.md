# micro-learning
This repository serves as a personal knowledge hub where I document and organize new concepts, technologies, and skills I learn daily. It's a growing library of insights across various domains. By maintaining this repository, I aim to track my learning progress and create a resource for future reference.


## Linux Soft Links and Hard Links

In Linux, links are a way to create references to files or directories. There are two types of links: 

- Soft Links (Symbolic Links): These are pointers to the original file or directory. They act like shortcuts, and if the original file is deleted, the soft link becomes broken. 

- Hard Links: These are direct references to the same data on the disk as the original file. Hard links remain functional even if the original file is deleted, as they point to the same inode.

In Linux and other Unix-like operating systems, an **iNode (index node)** is a data structure used by the filesystem to store information about a file or directory. Each file or directory on a filesystem has an associated iNode, which contains metadata about the file but not the file's name or its data. iNodes enable efficient file management and metadata storage.

Command Examples:
- Create a soft link:

```sh
ln -s <target_file> <link_name>
```

- Create a hard link:

```sh
ln <target_file> <link_name> 
```

### Soft linking with Full Paths and Using `$PATH`
Symbolic (soft) links can be used to make files or directories accessible from different paths. A common use case is linking executables in your `$PATH` so they can be run from anywhere. Here's how:

1. Create a Soft Link using absolute paths
If you have a file `/usr/local/bin/my_script.sh` and want to create a symbolic link in `/home/user/bin/`, you can use:

```sh
ln -s /usr/local/bin/my_script.sh /home/user/bin/my_script
```

- `/usr/local/bin/my_script.sh` is the original file (absolute path).
- `/home/user/bin/my_script` is the symbolic link.

Now, you can run `/home/user/bin/my_script` as if it were the original file.

2. Add the Link to a Directory in `$PATH`

To make your script executable from anywhere:

Ensure `/home/user/bin/` is in your `$PATH`. Check by running:

```sh
echo $PATH
```

If it’s not included, add it temporarily:

```sh
export PATH=$PATH:/home/user/bin/
```

Or, add it permanently by appending the line to `~/.bashrc` or `~/.bash_profile`:

```sh
echo 'export PATH=$PATH:/home/user/bin/' >> ~/.bashrc
source ~/.bashrc
```

Now, create the symbolic link in that directory:

```sh
ln -s /usr/local/bin/my_script.sh /home/user/bin/my_script
```

Run the script from anywhere:
```sh
my_script
```

3. Soft Link a Directory

To create a soft link to a directory, for example, linking `/mnt/data/projects` to `/home/user/work`:

```sh
ln -s /mnt/data/projects /home/user/work/projects
```

Now, accessing `/home/user/work/projects` will take you to `/mnt/data/projects`.

4. Overwrite an Existing Soft Link

If a symbolic link already exists and you want to replace it:

```sh
ln -sf /new/target/file /path/to/existing/link
```
- -f forces the replacement.

5. Verify a Symbolic Link

To confirm a symbolic link's target:

```sh
ls -l /path/to/link
```

Example output:

```sh
lrwxrwxrwx 1 user user 20 Jan 1 12:00 my_script -> /usr/local/bin/my_script.sh
```

With these commands, you can efficiently use soft links for file management and streamline workflows involving executables and $PATH.