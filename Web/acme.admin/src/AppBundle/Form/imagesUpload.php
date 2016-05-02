<?php
/**
 * Created by PhpStorm.
 * User: Djinodji
 * Date: 4/22/2016
 * Time: 7:04 AM
 */

namespace AppBundle\Form;


class imagesUpload
{
    private $files;

    /**
     * @var ArrayCollection
     */
    private $uploadedFiles;


    public function upload()
    {
        foreach($this->uploadedFiles as $uploadedFile)
        {
            $file = new File();

            /*
             * These lines could be moved to the File Class constructor to factorize
             * the File initialization and thus allow other classes to own Files
             */
            $path = sha1(uniqid(mt_rand(), true)).'.'.$uploadedFile->guessExtension();
            $file->setPath($path);
            $file->setSize($uploadedFile->getClientSize());
            $file->setName($uploadedFile->getClientOriginalName());

            $uploadedFile->move($this->getUploadRootDir(), $path);

            $this->getFiles()->add($file);
            $file->setDocument($this);

            unset($uploadedFile);
        }
    }
}