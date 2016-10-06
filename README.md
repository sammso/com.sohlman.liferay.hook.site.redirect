# Post Login Hook to redirect user to his site that is member

This hook redirects user to his site after the login. If there is multiple sites then it tries to find out first site from hierarchy.

## Build

```.sh
mvn package
```

## Install

Copy resulted war from ``target`` folder to Liferay 7.0 / DXP ``deploy`` folder.

*Warning hook is not designed to be used with users with huge amount member sites*