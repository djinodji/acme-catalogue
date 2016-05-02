<?php

namespace AppBundle\Entity;

/**
 * AlbumPhoto
 *
 */
class AlbumPhoto
{
    /**
     * @var integer
     */
    private $albumId;

    /**
     * @var integer
     */
    private $imageId;

    /**
     * @var integer
     */
    private $order;

    /**
     * @var integer
     */
    private $statut;

    /**
     * @var integer
     */
    private $id;


    /**
     * Set albumId
     *
     * @param integer $albumId
     *
     * @return AlbumPhoto
     */
    public function setAlbumId($albumId)
    {
        $this->albumId = $albumId;

        return $this;
    }

    /**
     * Get albumId
     *
     * @return integer
     */
    public function getAlbumId()
    {
        return $this->albumId;
    }

    /**
     * Set imageId
     *
     * @param integer $imageId
     *
     * @return AlbumPhoto
     */
    public function setImageId($imageId)
    {
        $this->imageId = $imageId;

        return $this;
    }

    /**
     * Get imageId
     *
     * @return integer
     */
    public function getImageId()
    {
        return $this->imageId;
    }

    /**
     * Set order
     *
     * @param integer $order
     *
     * @return AlbumPhoto
     */
    public function setOrder($order)
    {
        $this->order = $order;

        return $this;
    }
   static function cmp($a, $b)
    {
        return strcmp($a->albumId, $b->albumId);
    }
    /**
     * Get order
     *
     * @return integer
     */
    public function getOrder()
    {
        return $this->order;
    }

    /**
     * Set statut
     *
     * @param integer $statut
     *
     * @return AlbumPhoto
     */
    public function setStatut($statut)
    {
        $this->statut = $statut;

        return $this;
    }

    /**
     * Get statut
     *
     * @return integer
     */
    public function getStatut()
    {
        return $this->statut;
    }

    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }
    /**
     * @var integer
     */
    private $orderimage;


    /**
     * Set orderimage
     *
     * @param integer $orderimage
     *
     * @return AlbumPhoto
     */
    public function setOrderimage($orderimage)
    {
        $this->orderimage = $orderimage;

        return $this;
    }

    /**
     * Get orderimage
     *
     * @return integer
     */
    public function getOrderimage()
    {
        return $this->orderimage;
    }
}
