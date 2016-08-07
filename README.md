# ImageZoom

[![](https://jitpack.io/v/oliveiradev/image-zoom.svg)](https://jitpack.io/#oliveiradev/image-zoom)
[![AndroidArsenal](https://img.shields.io/badge/Android%20Arsenal-ImageZoom-green.svg?style=true)](https://android-arsenal.com/details/1/3502)


A simple lib for zoom images on event click 

<p align="center">
  <img src="art/12ynog.gif" alt="Image Zoom" />
</p>

#Usage

Is simple, use this component:

```xml
<com.github.oliveiradev.image_zoom.lib.widget.ImageZoom
        android:layout_width="width_dimensions"
        android:layout_height="height_dimensions"
        android:src="your_image" />
```

### Attributes

#### landScapeZoom
Starting version `0.3.0` is possible zooming on landscape orientation

```xml
<com.github.oliveiradev.image_zoom.lib.widget.ImageZoom
        android:layout_width="width_dimensions"
        android:layout_height="height_dimensions"
        app:landScapeZoom="true"
        android:src="your_image" />
```

#Install 

Add jitpack repositorie in your __build.gradle__ root level
```groovy
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
and , add this dependencie

```groovy
dependencies {
	compile 'com.github.oliveiradev:image-zoom:0.1.0'
}
```

#License
```
The MIT License (MIT)

Copyright (c) 2016 Felipe Oliveira

Permission is hereby granted, free of charge, to any person obtaining a 
copy of this software and associated documentation files (the "Software"), 
to deal in the Software without restriction, including without limitation 
the rights to use, copy, modify, merge, publish, distribute, sublicense, 
and/or sell copies of the Software, and to permit persons to whom the Software is 
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included 
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

```

